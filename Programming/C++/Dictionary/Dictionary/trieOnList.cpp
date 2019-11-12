#include "pch.h"
#include "trieOnList.h"


trieOnList::trieOnList()
{
	root = new Node;
	root->symbol = '#';
	root->nextSymbol = nullptr;
	root->nextNode = nullptr;
	root->word = "#";
}

void trieOnList::add_word(std::string w, std::string t)
{
	Node* pos = root;
	for (int i = 0; i < w.size(); i++)
	{
		while (pos->nextSymbol != nullptr)
		{
			if (pos->symbol == w[i])
			{
				break;
			}
			pos = pos->nextSymbol;
		}
		if (pos->symbol == w[i])
		{
			pos = pos->nextNode;
		}
		else
		{
			Node* q = new Node;
			q->symbol = w[i];
			q->word = "#";
			q->nextSymbol = nullptr;
			Node* n = new Node;
			n->symbol = '#';
			n->nextNode = nullptr;
			n->nextSymbol = nullptr;
			n->word = "#";
			q->nextNode = n;
			pos->nextSymbol = q;
			pos = pos->nextSymbol;
			pos = pos->nextNode;
		}
	}
	pos->word = t;
}

std::string trieOnList::translate(std::string w)
{
	Node* pos = root;
	for (int i = 0; i < w.size(); i++)
	{
		while (pos != nullptr)
		{
			if (pos->symbol == w[i])
			{
				break;
			}
			pos = pos->nextSymbol;
		}
		if (pos == nullptr)
		{
			return "Error! Dictionary doesn't contain this word";
		}
		else
		{
			pos = pos->nextNode;
		}
	}
	if (pos->word == "#")
	{
		return "Error! Dictionary doesn't contain this word";
	}
	else
	{
		return pos->word;
	}
}
trieOnList::~trieOnList()
{
	delete root;
}
