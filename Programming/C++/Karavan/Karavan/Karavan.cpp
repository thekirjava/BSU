#include "pch.h"
#include <iostream>
#include <fstream>
#include <queue>
#include <vector>
#include <stack>
using namespace std;

int move_i[4] = { 1, -1, 0, 0 };
int move_j[4] = { 0, 0, 1, -1 };
struct position
{
	int i;
	int j;
};
struct tile
{
	int H;
	bool vis;
	position prev;
};
int main()
{
	ifstream fin("input.txt");
	ofstream fout("output.txt");
	int N, M;
	fin >> N >> M;
	tile T;
	T.H = INT16_MAX;
	T.vis = false;
	T.prev.i = -1;
	T.prev.j = -1;
	vector <vector <tile> > desert(N + 2, vector <tile>(M + 2, T));
	for (int i = 1; i <= N; i++)
	{
		for (int j = 1; j <= M; j++)
		{
			fin >> desert[i][j].H;
		}
	}
	position start, finish;
	fin >> start.i >> start.j;
	fin >> finish.i >> finish.j;
	queue <position> path;
	path.push(start);
	position cur;
	desert[start.i][start.j].vis = true;
	while (path.size() != 0)
	{
		cur = path.front();
		if ((cur.i == finish.i) && (cur.j == finish.j))
		{
			break;
		}
		for (int i = 0; i < 4; i++)
		{
			if ((desert[cur.i][cur.j].H >= desert[cur.i + move_i[i]][cur.j + move_j[i]].H) && (desert[cur.i + move_i[i]][cur.j + move_j[i]].vis == false))
			{
				desert[cur.i + move_i[i]][cur.j + move_j[i]].prev.i = cur.i;
				desert[cur.i + move_i[i]][cur.j + move_j[i]].prev.j = cur.j;
				cur.i += move_i[i];
				cur.j += move_j[i];
				path.push(cur);
				desert[cur.i][cur.j].vis = true;
				cur.i -= move_i[i];
				cur.j -= move_j[i];
			}
		}
		path.pop();
	}
	if ((desert[finish.i][finish.j].prev.i == -1) && (desert[finish.i][finish.j].prev.j == -1))
	{
		fout << "NO";
		return 0;
	}
	stack <position> ans;
	cur = finish;
	while ((cur.i != -1) && (cur.j != -1))
	{
		ans.push(cur);
		cur = desert[cur.i][cur.j].prev;
	}
	fout << ans.size() << '\n';
	while (ans.size() != 0)
	{
		fout << ans.top().i << " " << ans.top().j << '\n';
		ans.pop();
	}
	return 0;
}
