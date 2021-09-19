//
// Created by JUSTONEIDEA on 19.09.2021.
//

#include "Vigenere.h"

#include <utility>

Vigenere::Vigenere(std::string  keyword):keyword(std::move(keyword)) {

}

std::string Vigenere::encrypt(const std::string& data) const {
    return processString(data, 1);
}

std::string Vigenere::decrypt(const std::string &data) const {
    return processString(data, -1);
}

std::string Vigenere::processString(const std::string &data, int sign) const {
    std::string processed;
    processed.reserve(data.size());
    for (int i = 0; i < data.size(); ++i) {
        if (!isalpha(data[i])) {
            processed.push_back(data[i]);
            continue;
        }
        int cur = data[i] - LANG_OFFSET;
        cur += (keyword[i % keyword.length()] - LANG_OFFSET) * sign;
        if (cur < 0) {
            cur += LANG_LENGTH;
        }
        cur %= LANG_LENGTH;
        processed.push_back(cur + LANG_OFFSET);
    }
    return processed;
}


