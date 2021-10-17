#pragma once

#include "HashAlg.h"

class MerkleDamgard : public HashAlg{
protected:
    std::string stack;
    uint64_t clen;

public:
    MerkleDamgard();
    virtual ~MerkleDamgard();
    virtual void update(const std::string & str) = 0;
    virtual std::size_t blocksize() const = 0;  // blocksize in bits
};
