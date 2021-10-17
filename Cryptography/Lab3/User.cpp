#include "User.h"

#include <utility>
#include <iostream>
#include <fstream>
#include "Hashes/SHA1.h"

User::User(const EllipticGroup &ellipticGroup, std::string userName) : ellipticGroup(ellipticGroup),
                                                                       userName(std::move(userName)),
                                                                       secretKey(0),
                                                                       publicKey({-1, -1})
                                                                       {

    while ((publicKey == EllipticGroup::Point{-1, -1})  || (publicKey == this->ellipticGroup.getG())) {
        secretKey = 0;
        while (secretKey == 0) {
            secretKey = this->ellipticGroup.getRandomIdx();
        }
        publicKey = this->ellipticGroup.getG() * secretKey;
    }
}

void User::printPublicKey() {
    std::ofstream fout(this->userName + "_public.txt");
    fout << publicKey.x << ' ' << publicKey.y;
}

void User::getOtherPublicKey(const std::string &otherUserName) {
    std::ifstream fin(otherUserName + "_public.txt");
    EllipticGroup::Point otherPublic{};
    fin >> otherPublic.x >> otherPublic.y;
    this->commonSecretKey = otherPublic * this->secretKey;
    std::cout << this->userName << " secret key: " << this->secretKey << '\n';
    std::cout << this->userName << " public key: " << this->publicKey << '\n';
    std::cout << this->userName << " common secret key: " << this->commonSecretKey << '\n';
}

void User::signMessage(const std::string &message) const {
    SHA1 sha1(message);
    std::string hash = sha1.digest();
    for (char& c: hash) {
        std::cout <<static_cast<int>(c) << ' ';
    }
    hash = sha1.hexdigest();
    for (char& c: hash) {
        std::cout <<static_cast<int>(c) << ' ';
    }
    std::cout << '\n' << hash;
}
