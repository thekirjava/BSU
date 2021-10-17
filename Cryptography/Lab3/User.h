#pragma once
#include "EllipticGroup.h"
class User {
public:
    User(const EllipticGroup& ellipticGroup, std::string userName);
    void printPublicKey();
    void getOtherPublicKey(const std::string& otherUserName);
    void signMessage(const std::string& message) const;
private:
    const EllipticGroup& ellipticGroup;
    std::string userName;
    int secretKey;
    EllipticGroup::Point publicKey;
    EllipticGroup::Point commonSecretKey;
};
