#include "pch.h"
#include "Localization_Engine.h"
#include <iostream>
#include <string>

static HMODULE hLib;

void Init(const char* lang)
{
	if (lang == nullptr) {
		FreeLibrary(hLib);
		return;
	}
	std::string s("Localization_" + (std::string)lang);
	hLib = LoadLibraryA(s.c_str());
	if (hLib == NULL) {
		std::cerr << "Library isn't found\n";
		return;
	}
}

const char* GetFirst()
{
	if (hLib != NULL) {
		const char** result;
		(FARPROC&)result = GetProcAddress(hLib, "first");
		return *result;
	}
	return "%{first}%";
}

const char* GetSecond()
{
	if (hLib != NULL) {
		const char** result;
		(FARPROC&)result = GetProcAddress(hLib, "second");
		return *result;
	}
	return "%{second}%";
}
