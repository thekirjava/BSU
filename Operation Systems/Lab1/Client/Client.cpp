#include <iostream>
#include "Localization_Engine.h"
int main(int argc, char* argv[])
{
	if (argc == 1) {
		Init("BY");
	}
	else {
		Init(argv[1]);
	}
	std::cout << GetFirst() << " " << GetSecond() << '\n';
	Init(nullptr);
}

