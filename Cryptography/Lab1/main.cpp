#include <iostream>
#include <string>
#include <algorithm>
#include "Vigenere.h"
#include "Decrypter.h"
int main() {
    std::string text, key;
    std::getline(std::cin, text);
    std::for_each(begin(text), end(text), [](char& c) {c = std::tolower(c);});
    std::cin >> key;
    std::for_each(begin(key), end(key), [](char& c) {c = std::tolower(c);});
    Vigenere crypt(key);
    text = crypt.encrypt(text);
    std::cout << text << std::endl;
    Decrypter::Kasiski kasiski(3);
    int keyLength = kasiski.getKeywordLength(text);
    std::cout<< keyLength << std::endl;
    Decrypter::FrequencyAnalyzer analyzer(keyLength);
    std::string myKey = analyzer.findKeyword(text);
    std::cout << myKey << std::endl;
    Vigenere decrypt(myKey);
    std::cout << decrypt.decrypt(text) << std::endl;
}
