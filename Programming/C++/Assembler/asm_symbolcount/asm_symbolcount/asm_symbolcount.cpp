#include "pch.h"
#include <iostream>

using namespace std;

int main()
{
	int N;
	cin >> N;
	char a[10000];
	for (int i = 0; i < N; i++)
	{
		cin >> a[i];
	}
	int ans[256];
	for (int i = 0; i < 256; i++)
	{
		ans[i] = 0;
	}
	_asm
	{
		lea eax, ans
		xor esi, esi
		loop1:
		cmp esi, N
		JGE end1
			xor ecx, ecx
			mov cl , a[esi]
			inc [eax][ecx*4]
			inc esi
			jmp loop1
		end1:

	}
	for (int i = 32; i < 256; i++)
	{
		cout << (char)i << " " << ans[i]<<'\n';
	}
	return 0;
}
