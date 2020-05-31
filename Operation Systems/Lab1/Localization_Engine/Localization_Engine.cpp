#include "pch.h"
#include "Localization_Engine.h"
#include <iostream>
#include <string>
static HMODULE lib;
void Init(const char* lang)
{
	if (lang == nullptr) {
		FreeLibrary(lib);
		return;
	}
	std::string s("Localization_" + (std::string)lang);
	lib = LoadLibraryA(s.c_str());
	if (lib == NULL) {
		std::cerr << "Library isn't found\n";
		return;
	}
}

const char* GetFirst()
{
	if (lib != NULL) {
		const char** result;
		(FARPROC&)result = GetProcAddress(lib, "first");
		return *result;
	}
	return "%{first}%";
}

const char* GetSecond()
{
	if (lib != NULL) {
		const char** result;
		(FARPROC&)result = GetProcAddress(lib, "second");
		return *result;
	}
	return "%{second}%";
}
