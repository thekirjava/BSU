#include "pch.h"
#include <iostream>

using namespace std;

int main()
{
	int N;
	cin >> N;
	int* a = new int[N];
	for (int i = 0; i < N; i++)
	{
		cin >> a[i];
	}
	int ans = 0;
	_asm
	{
		mov ebx, a
		xor esi, esi
		xor eax, eax
		in1:
		cmp esi, N
		je out1
		xor eax, [ebx][esi*4]
		inc esi
		jmp in1
		out1:
		mov ans, eax
	}
	cout << ans;
	return 0;
}

