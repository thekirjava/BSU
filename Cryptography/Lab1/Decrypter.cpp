#include <algorithm>
#include <cmath>
#include "Decrypter.h"

Decrypter::Kasiski::Kasiski(int length) : l(length) {}

int Decrypter::Kasiski::getKeywordLength(const std::string &data) const {
    return this->listGCD(this->buildDiffs(this->buildGramms(data)));
}

Decrypter::GrammsMap Decrypter::Kasiski::buildGramms(const std::string &data) const {
    GrammsMap gramms;
    for (int i = 0; i < data.size() - l; ++i) {
        std::string substr = data.substr(i, l);
        bool flag = false;
        for (const char &c:substr) {
            flag |= !isalpha(c);
        }
        if (flag) {
            continue;
        }
        if (gramms.find(substr) != gramms.end()) {
            continue;
        }
        auto matches = kmpAlgorithm(data, substr);
        if (matches.size() > 1) {
            gramms.insert({substr, matches});
        }
    }
    return gramms;
}

std::vector<int> Decrypter::Kasiski::kmpAlgorithm(const std::string &data, const std::string &substring) const {
    std::string text = substring + "#" + data;
    std::vector<int> prefixFunction(text.size());
    std::vector<int> result;
    for (int i = 1; i < prefixFunction.size(); ++i) {
        int j = prefixFunction[i - 1];
        while ((j > 0) && (text[i] != text[j])) {
            j = prefixFunction[j - 1];
        }
        if (text[i] == text[j]) {
            j++;
        }
        prefixFunction[i] = j;
        if (j == substring.size()) {
            result.push_back(i - 2 * substring.size());
        }
    }
    return result;
}

std::vector<int> Decrypter::Kasiski::buildDiffs(const Decrypter::GrammsMap &gramms) const {
    std::map<int, int> diffs;
    for (const auto&[grammKey, matches]:gramms) {
        for (int i = 0; i < matches.size() - 1; ++i) {
            int key = matches[i + 1] - matches[i];
            diffs[key] += 1;
        }
    }
    int common_diff = std::max_element(begin(diffs), end(diffs), [](const auto &lhs, const auto &rhs) {
        return lhs.second < rhs.second;
    })->second;
    int minAcceptedDiff = std::ceil(common_diff * DIFF_THRESHOLD);
    std::vector<int> result;
    for (const auto&[key, matches]:diffs) {
        if (diffs.at(key) > minAcceptedDiff) {
            result.push_back(key);
        }
    }
    return result;
}

int Decrypter::Kasiski::listGCD(const std::vector<int> &diffs) const {
    int gcd = diffs[0];
    for (int i = 1; i < diffs.size(); ++i) {
        gcd = binaryGCD(gcd, diffs[i]);
    }
    return gcd;
}

int Decrypter::Kasiski::binaryGCD(int x, int y) const {
    if ((x == y) || (y == 0)) {
        return x;
    }
    if (x == 0) {
        return y;
    }
    if (x % 2 == 0) {
        if (y % 2 == 0) {
            return binaryGCD(x / 2, y / 2) * 2;
        } else {
            return binaryGCD(x / 2, y);
        }
    }
    if (y % 2 == 0) {
        return binaryGCD(x, y / 2);
    }
    if (x > y) {
        return binaryGCD((x - y) / 2, y);
    } else {
        return binaryGCD((y - x) / 2, x);
    }
}

Decrypter::FrequencyAnalyzer::FrequencyAnalyzer(int length) : length(length) {}

std::string Decrypter::FrequencyAnalyzer::findKeyword(const std::string &text) const {
    std::vector<std::string> parts;
    for (int i = 0; i < length; i++) {
        std::string part;
        for (int j = i; j < text.size(); j += length) {
            part += text[j];
        }
        parts.push_back(part);
    }
    std::string key;
    for (const auto &part:parts) {
        key.push_back(findKeywordLetter(part));
    }
    return key;
}

char Decrypter::FrequencyAnalyzer::findKeywordLetter(const std::string &part) const {
    auto frequencies = countFrequencies(part);
    std::map<int, int> shifts;
    for (const auto &elem:frequencies) {
        double diff = std::fabs(elem.freq - FREQUENCIES.at('a'));
        char closest = 'a';
        for (const auto&[key, value]:FREQUENCIES) {
            if (std::fabs(elem.freq - value) < diff) {
                diff = std::fabs(elem.freq - value);
                closest = key;
            }
        }
        int shift = elem.symbol - closest;
        if (shift < 0) {
            shift += FREQUENCIES.size();
        }
        shifts[shift]++;
    }
    int resultShift = 0;
    int resultShiftCount = 0;
    for (const auto&[key, value]:shifts) {
        if (value > resultShiftCount) {
            resultShift = key;
            resultShiftCount = value;
        }
    }
    return 'a' + resultShift;
}

std::vector<Decrypter::FrequencyAnalyzer::CharFrequency>
Decrypter::FrequencyAnalyzer::countFrequencies(const std::string &text) const {
    std::map<char, int> lettersCount;
    for (const auto&[key, value]:FREQUENCIES) {
        lettersCount[key] = 0;
    }
    int nonLetters = 0;
    for (const auto &c:text) {
        if (('a' <= c) && (c <= 'z')) {
            lettersCount[c] += 1;
        } else {
            nonLetters++;
        }
    }
    int textLength = text.size() - nonLetters;
    std::vector<CharFrequency> answer;
    answer.reserve(lettersCount.size());
    for (const auto&[key, value]: lettersCount) {
        answer.push_back({key, double(value) / textLength});
    }
    std::sort(rbegin(answer), rend(answer));
    return answer;
}

bool
Decrypter::FrequencyAnalyzer::CharFrequency::operator<(const Decrypter::FrequencyAnalyzer::CharFrequency &other) const {
    return this->freq < other.freq;
}
