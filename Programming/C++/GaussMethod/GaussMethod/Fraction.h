#pragma once
#include "pch.h"
#include <iostream>
#include <vector>
#include <algorithm>
#include <fstream>
#include <string>

using namespace std;

class Fraction
{
	friend istream& operator>>(istream& input, Fraction& F);
	friend ostream& operator<<(ostream& output, const Fraction& F);
public:
	Fraction();
	Fraction(int a, int b);
	Fraction(Fraction& F);
	~Fraction();
	friend void reduction(Fraction& F);
	bool operator<(const Fraction& F);
	bool operator<=(const Fraction& F);
	bool operator>(const Fraction& F);
	bool operator>=(const Fraction& F);
	bool operator==(const Fraction& F);
	bool operator!=(const Fraction& F);
	Fraction& operator=(const Fraction& F);
	Fraction& operator+=(const Fraction& F);
	Fraction& operator-=(const Fraction& F);
	Fraction& operator*=(const Fraction& F);
	Fraction& operator/=(const Fraction& F);
	const Fraction operator+(const Fraction& F);
	const Fraction operator-(const Fraction& F);
	const Fraction operator*(const Fraction& F);
	const Fraction operator/(const Fraction& F);
private:
	int numerator;
	int denominator;
	int gcd();
	void pos_neg();
};