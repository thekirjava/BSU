#pragma once

#include <iostream>

#include "../common/includes.h"

class HashAlg{
public:
    HashAlg();
    virtual ~HashAlg();
    virtual std::string hexdigest() = 0;
    std::string digest();
    virtual std::size_t digestsize() const = 0; // digest size in bits
};