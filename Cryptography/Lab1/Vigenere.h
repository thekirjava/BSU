#pragma once
#include <string>

class Vigenere {
public:
    Vigenere(std::string  keyword);
    std::string encrypt(const std::string& data) const;
    std::string decrypt(const std::string& data) const;
private:
    std::string processString(const std::string& data, int sign) const;
    std::string keyword;
    const int LANG_OFFSET = 'a';
    const int LANG_LENGTH = 26;
};

