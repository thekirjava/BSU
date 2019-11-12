#include "pch.h"
#include <iostream>
#include <fstream>
#include <vector>
#include <queue>

using namespace std;

int main()
{
	ifstream in("input.txt");
	ofstream out("output.txt");
	int M, N;
	in >> M >> N;
	vector < vector<int> > castle(M, vector<int>(N));
	for (int i = 0; i < M; i++)
	{
		for (int j = 0; j < N; j++)
		{
			in >> castle[i][j];
		}
	}
	vector <vector <int> > rooms(M, vector<int>(N, -1));
	vector <int> roomsize;
	int roomcnt = 0;
	for (int i = 0; i < M; i++)
	{
		for (int j = 0; j < N; j++)
		{
			if (rooms[i][j] == -1)
			{
				roomcnt++;
				queue<pair<int, int> > way;
				way.push(make_pair(i, j));
				roomsize.push_back(0);
				rooms[i][j] = roomcnt;
				while (way.size() != 0)
				{
					roomsize[roomcnt - 1]++;
					if (castle[way.front().first][way.front().second] >= 8)
					{
						castle[way.front().first][way.front().second] -= 8;
					}
					else
					{
						if (rooms[way.front().first + 1][way.front().second] == -1)
						{
							way.push(make_pair(way.front().first + 1, way.front().second));
							rooms[way.front().first + 1][way.front().second] = roomcnt;
						}
					}
					if (castle[way.front().first][way.front().second] >= 4)
					{
						castle[way.front().first][way.front().second] -= 4;
					}
					else
					{
						if (rooms[way.front().first][way.front().second + 1] == -1)
						{
							way.push(make_pair(way.front().first, way.front().second + 1));
							rooms[way.front().first][way.front().second + 1] = roomcnt;
						}
					}
					if (castle[way.front().first][way.front().second] >= 2)
					{
						castle[way.front().first][way.front().second] -= 2;
					}
					else
					{
						if (rooms[way.front().first - 1][way.front().second] == -1)
						{
							way.push(make_pair(way.front().first - 1, way.front().second));
							rooms[way.front().first - 1][way.front().second] = roomcnt;
						}
					}
					if (castle[way.front().first][way.front().second] >= 1)
					{
						castle[way.front().first][way.front().second] -= 1;
					}
					else
					{
						if (rooms[way.front().first][way.front().second - 1] == -1)
						{
							way.push(make_pair(way.front().first, way.front().second - 1));
							rooms[way.front().first][way.front().second - 1] = roomcnt;
						}
					}
					way.pop();
				}
			}
		}
	}
	out << "Number of rooms:\n";
	out << roomcnt << '\n';
	int ans = roomsize[0];
	for (int i = 0; i < roomcnt; i++)
	{
		ans = max(ans, roomsize[i]);
	}
	out << "Biggest room square:\n";
	out << ans << '\n';
	ans = 0;
	pair <int, int> room_1;
	pair <int, int> room_2;
	for (int i = 0; i < M; i++)
	{
		for (int j = 0; j < N; j++)
		{
			if (i + 1 != M)
			{
				if (rooms[i][j] != rooms[i + 1][j])
				{
					if (roomsize[rooms[i][j] - 1] + roomsize[rooms[i + 1][j] - 1] > ans)
					{
						ans = roomsize[rooms[i][j] - 1] + roomsize[rooms[i + 1][j] - 1];
						room_1.first = i;
						room_1.second = j;
						room_2.first = i + 1;
						room_2.second = j;
					}
				}
			}
			if (j + 1 != N)
			{
				if (rooms[i][j] != rooms[i][j + 1])
				{
					if (roomsize[rooms[i][j] - 1] + roomsize[rooms[i][j + 1] - 1] > ans)
					{
						ans = roomsize[rooms[i][j] - 1] + roomsize[rooms[i][j + 1] - 1];
						room_1.first = i;
						room_1.second = j;
						room_2.first = i;
						room_2.second = j + 1;
					}
				}
			}
		}
	}
	out << "Square of two rooms and position:\n";
	out << ans << '\n' << room_1.first + 1 << " " << room_1.second + 1 << '\n' << room_2.first + 1 << " " << room_2.second + 1;
	return 0;
}
