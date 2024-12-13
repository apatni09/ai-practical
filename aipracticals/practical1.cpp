#include <bits/stdc++.h>
using namespace std;
typedef pair<int, int> pii;

void printpath(map<pii, pii> &mp, pii u) {
    if (u.first == 0 && u.second == 0) {
        cout << 0 << " " << 0 << endl;
        return;
    }
    printpath(mp, mp[u]);
    cout << u.first << " " << u.second << endl;
}

bool DFS(int a, int b, int target, map<pii, int> &visited, map<pii, pii> &mp, pii u) {
    if (u.first > a || u.second > b || u.first < 0 || u.second < 0 || visited[u] == 1) {
        return false;
    }
    
    visited[u] = 1;

    if (u.first == target || u.second == target) {
        printpath(mp, u);
        if (u.first == target && u.second != 0)
            cout << u.first << " " << 0 << endl;
        else if (u.second == target && u.first != 0)
            cout << 0 << " " << u.second << endl;
        return true;
    }

    // completely fill the jug 2
    if (visited[{u.first, b}] != 1) {
        mp[{u.first, b}] = u;
        if (DFS(a, b, target, visited, mp, {u.first, b}))
            return true;
    }

    // completely fill the jug 1
    if (visited[{a, u.second}] != 1) {
        mp[{a, u.second}] = u;
        if (DFS(a, b, target, visited, mp, {a, u.second}))
            return true;
    }

    // transfer jug 1 -> jug 2
    int d = b - u.second;
    if (u.first >= d) {
        int c = u.first - d;
        if (visited[{c, b}] != 1) {
            mp[{c, b}] = u;
            if (DFS(a, b, target, visited, mp, {c, b}))
                return true;
        }
    } else {
        int c = u.first + u.second;
        if (visited[{0, c}] != 1) {
            mp[{0, c}] = u;
            if (DFS(a, b, target, visited, mp, {0, c}))
                return true;
        }
    }


    // transfer jug 2 -> jug 1
    d = a - u.first;
    if (u.second >= d) {
        int c = u.second - d;
        if (visited[{a, c}] != 1) {
            mp[{a, c}] = u;
            if (DFS(a, b, target, visited, mp, {a, c}))
                return true;
        }
    } else {
        int c = u.first + u.second;
        if (visited[{c, 0}] != 1) {
            mp[{c, 0}] = u;
            if (DFS(a, b, target, visited, mp, {c, 0}))
                return true;
        }
    }

    // empty the jug 2
    if (visited[{u.first, 0}] != 1) {
        mp[{u.first, 0}] = u;
        if (DFS(a, b, target, visited, mp, {u.first, 0}))
            return true;
    }

    // empty the jug 1
    if (visited[{0, u.second}] != 1) {
        mp[{0, u.second}] = u;
        if (DFS(a, b, target, visited, mp, {0, u.second}))
            return true;
    }

    return false;
}

void solve(int a, int b, int target) {
    map<pii, int> visited;
    map<pii, pii> mp;
    pii start = {0, 0};
    if (!DFS(a, b, target, visited, mp, start)) {
        cout << "No solution" << endl;
    }
}

int main() {
    int Jug1 = 4, Jug2 = 3, target = 2;
    cout << "Path from initial state to solution state ::\n";
    solve(Jug1, Jug2, target);
    return 0;
}