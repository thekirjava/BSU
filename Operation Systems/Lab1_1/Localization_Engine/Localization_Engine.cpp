#include "pch.h"
#include "Localization_Engine.h"
#include <iostream>
#include <string>

static HMODULE hLib = LoadLibraryA("Localization_Engine.old.dll");
typedef void(cdecl* get_old_init)(const char*);
typedef const char* (cdecl* get_old_first)(void);
void Init(const char* lang)
{
	get_old_init func = (get_old_init)GetProcAddress(hLib, "Init");
	func(lang);
}

const char* GetFirst()
{
	if (hLib != NULL) {
		const char* result;
		get_old_first func =(get_old_first) GetProcAddress(hLib, "GetFirst");
		result = func();
		return result;
	}
	return "%{first}%";
}

const char* GetSecond()
{
	return "Hacked";
}
