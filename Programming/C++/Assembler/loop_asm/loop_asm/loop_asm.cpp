#include "pch.h"
#include <iostream>

using namespace std;

int main()
{
	unsigned int ans = 0, i = 0, j = 0;
	_asm
	{
		mov eax, ans
		mov i, 1
		mov j, 11
	in1:
		cmp i, 10
		JA out1
		mov edx, i
		imul edx, edx
	in2:
		cmp j, 20
		JA out2
		add eax, edx
		add eax, j
		add j, 1
		jmp in2
	out2:
		mov j, 11
		add i, 1
		jmp in1
	out1:
		mov ans, eax
	}
	cout << ans;
	return 0;
}
