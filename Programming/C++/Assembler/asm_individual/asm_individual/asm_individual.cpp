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
	int cnt = 0, mid = 0, midcnt = 0, sum = 0;
	_asm
	{
		mov ebx, a
		xor esi, esi
	loopbegin_1:
		cmp esi, N
		je loopend_1
		mov ecx, [ebx][esi*4]
		inc esi
		cmp ecx, 0
		je equall
		jg positive
		jl negative
	equall:
		add cnt, 1
		jmp loopbegin_1
	positive:
		add mid, ecx
		add midcnt, 1
		jmp loopbegin_1
	negative:
		add sum, ecx
		jmp loopbegin_1
	loopend_1:
		cmp midcnt, 0
		je out1
		mov eax, mid;
		cdq
		idiv midcnt
		mov mid, eax
	out1:
	}
	cout << "Ariphmetic mean of positive - " << mid << '\n';
	cout << "Sum of negative - " << sum << '\n';
	cout << "Null count - " << cnt << '\n';
	return 0;
}
