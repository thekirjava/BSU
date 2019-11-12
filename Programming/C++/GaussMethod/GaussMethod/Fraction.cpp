#include "pch.h"
#include "Fraction.h"
#include <iostream>
#include <string>

using namespace std;

istream& operator>>(istream& input, Fraction& F)
{
	string s;
	input >> s;
	int check = 1;
	if (s[0] == '-')
	{
		s.erase(s.begin());
		check = -1;
	}
	F.numerator = 0;
	F.denominator = 0;
	if (s.find_first_of('.') != s.npos)
	{
		int i = 0;
		while (s[i] != '.')
		{
			F.numerator *= 10;
			F.numerator += s[i] - '0';
			i++;
		}
		i++;
		F.denominator = 1;
		while (i < s.size())
		{
			F.numerator *= 10;
			F.numerator += s[i] - '0';
			F.denominator *= 10;
			i++;
		}
	}
	else
	{
		if (s.find_first_of('/') != s.npos)
		{
			int i = 0;
			while (s[i] != '/')
			{
				F.numerator *= 10;
				F.numerator += s[i] - '0';
				i++;
			}
			i++;
			while (i < s.size())
			{
				F.denominator *= 10;
				F.denominator += s[i] - '0';
				i++;
			}
		}
		else
		{
			F.denominator = 1;
			for (int i = 0; i < s.size(); i++)
			{
				F.numerator *= 10;
				F.numerator += s[i] - '0';
			}
		}
	}
	F.numerator *= check;
	return input;
}
ostream& operator<<(ostream& output, const Fraction& F)
{
	output << F.numerator << '/' << F.denominator;
	return output;
}
Fraction::Fraction() :numerator(0), denominator(1) {}
Fraction::Fraction(int a, int b) :numerator(a), denominator(b) {}
Fraction::Fraction(Fraction& f) : numerator(f.numerator), denominator(f.denominator) {}
Fraction::~Fraction()
{
	numerator = 0;
	denominator = 0;
}
void Fraction::pos_neg()
{
	if (denominator<0)
	{
		numerator = -numerator;
		denominator = -denominator;
	}
}
int Fraction::gcd()
{
	int x = numerator;
	int y = denominator;
	if (x < y)
	{
		swap(x, y);
	}
	while (y != 0)
	{
		x %= y;
		swap(x, y);
	}
	return x;
}
void reduction(Fraction& F)
{
	int x = F.gcd();
	F.numerator /= x;
	F.denominator /= x;
	F.pos_neg();

}
bool Fraction::operator<(const Fraction& F)
{
	return((numerator*F.denominator) < (denominator*F.numerator));
}
bool Fraction::operator<=(const Fraction& F)
{
	return((numerator*F.denominator) <= (denominator*F.numerator));
}
bool Fraction::operator>(const Fraction& F)
{
	return((numerator*F.denominator) > (denominator*F.numerator));
}
bool Fraction::operator>=(const Fraction& F)
{
	return((numerator*F.denominator) >= (denominator*F.numerator));
}
bool Fraction::operator==(const Fraction& F)
{
	return((numerator*F.denominator) == (denominator*F.numerator));
}
bool Fraction::operator!=(const Fraction& F)
{
	return((numerator*F.denominator) != (denominator*F.numerator));
}
Fraction& Fraction::operator=(const Fraction& F)
{
	numerator = F.numerator;
	denominator = F.denominator;
	return *this;
}
Fraction& Fraction::operator+=(const Fraction& F)
{
	numerator *= F.denominator;
	numerator += (F.numerator*denominator);
	denominator *= F.denominator;
	return *this;
}
Fraction& Fraction::operator-=(const Fraction& F)
{
	numerator *= F.denominator;
	numerator -= (F.numerator*denominator);
	denominator *= F.denominator;
	return *this;
}
Fraction& Fraction::operator*=(const Fraction& F)
{
	numerator *= F.numerator;
	denominator *= F.denominator;
	return *this;
}
Fraction& Fraction::operator/=(const Fraction& F)
{
	numerator *= F.denominator;
	denominator *= F.numerator;
	return *this;
}
const Fraction Fraction::operator+(const Fraction& F)
{
	Fraction ans;
	ans.numerator = (numerator*F.denominator) + (denominator*F.numerator);
	ans.denominator = denominator * F.denominator;
	return ans;
}
const Fraction Fraction::operator-(const Fraction& F)
{
	Fraction ans;
	ans.numerator = (numerator*F.denominator) - (denominator*F.numerator);
	ans.denominator = denominator * F.denominator;
	return ans;
}
const Fraction Fraction::operator*(const Fraction& F)
{
	Fraction ans;
	ans.numerator = numerator * F.numerator;
	ans.denominator = denominator * F.denominator;
	return ans;
}
const Fraction Fraction::operator/(const Fraction& F)
{
	Fraction ans;
	ans.numerator = numerator * F.denominator;
	ans.denominator = denominator * F.numerator;
	return ans;
}