#pragma once

#include "../common/cryptomath.h"
#include "../common/includes.h"
#include "MerkleDamgard.h"

class SHA1 : public MerkleDamgard{
private:
    struct context{
        uint32_t h0, h1, h2, h3, h4;

        context(uint32_t h0, uint32_t h1, uint32_t h2, uint32_t h3, uint32_t h4) :
                h0(h0),
                h1(h1),
                h2(h2),
                h3(h3),
                h4(h4)
        {}
        ~context(){
            h0 = h1 = h2 = h3 = h4 = 0;
        }
    };

    context ctx;

    void calc(const std::string & data, context & state) const;

public:
    SHA1();
    SHA1(const std::string & str);
    void update(const std::string & str);
    std::string hexdigest();
    std::size_t blocksize() const;
    std::size_t digestsize() const;
};