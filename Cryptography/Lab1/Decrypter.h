#pragma once

#include <string>
#include <map>
#include <vector>

namespace Decrypter {
    using GrammsMap = std::map<std::string, std::vector<int>>;

    class Kasiski {
    public:
        Kasiski(int length);

        int getKeywordLength(const std::string &data) const;

    private:
        GrammsMap buildGramms(const std::string &data) const;

        std::vector<int> kmpAlgorithm(const std::string &data, const std::string &substring) const;

        std::vector<int> buildDiffs(const GrammsMap &gramms) const;

        int binaryGCD(int x, int y) const;

        int listGCD(const std::vector<int> &diffs) const;

        int l;
        const double DIFF_THRESHOLD = 0.1;
    };

    class FrequencyAnalyzer {
        struct CharFrequency  {
            char symbol;
            double freq;
            bool operator<(const CharFrequency& other) const;
        };
    public:
        FrequencyAnalyzer(int length);

        std::string findKeyword(const std::string &text) const;

    private:
        char findKeywordLetter(const std::string &part) const;
        std::vector <CharFrequency> countFrequencies(const std::string& text) const;
        int length;
        const std::map<char, double> FREQUENCIES = {
                {'a', 0.08167},
                {'b',0.01492},
                {'c',0.02782},
                {'d',0.04253},
                {'e',0.12702},
                {'f',0.0228},
                {'g',0.02015},
                {'h',0.06094},
                {'i',0.06966},
                {'j',0.00153},
                {'k',0.00772},
                {'l',0.04025},
                {'m',0.02406},
                {'n',0.06749},
                {'o',0.07507},
                {'p',0.01929},
                {'q',0.00095},
                {'r',0.05987},
                {'s',0.06327},
                {'t',0.09056},
                {'u',0.02758},
                {'v',0.00978},
                {'w',0.0236},
                {'x',0.0015},
                {'y',0.01974},
                {'z',0.00074}
        };
    };
};

