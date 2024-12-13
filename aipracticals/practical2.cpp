#include <iostream>
#include <vector>
#include <queue>
#include <set>
#include <tuple>

using namespace std;

class State {
public:
    int missionaries;
    int cannibals;
    int boat; // 0 for left, 1 for right

    State(int missionaries, int cannibals, int boat)
        : missionaries(missionaries), cannibals(cannibals), boat(boat) {}

    bool isValid() const {
        if (missionaries < 0 || cannibals < 0 || missionaries > 3 || cannibals > 3) {
            return false;
        }
        if ((missionaries > 0 && missionaries < cannibals) || 
            (3 - missionaries > 0 && 3 - missionaries < 3 - cannibals)) {
            return false;
        }
        return true;
    }

    bool isGoal() const {
        return missionaries == 0 && cannibals == 0 && boat == 1;
    }

    bool operator==(const State& other) const {
        return missionaries == other.missionaries && cannibals == other.cannibals && boat == other.boat;
    }

    bool operator<(const State& other) const {
        return tie(missionaries, cannibals, boat) < tie(other.missionaries, other.cannibals, other.boat);
    }

    friend ostream& operator<<(ostream& os, const State& state) {
        os << "State{"
           << "missionaries=" << state.missionaries
           << ", cannibals=" << state.cannibals
           << ", boat=" << (state.boat == 0 ? "left" : "right")
           << '}';
        return os;
    }
};

vector<State> generateNextStates(const State& currentState) {
    vector<State> nextStates;
    int missionariesMove[] = {0, 0, 1, 1, 2};
    int cannibalsMove[] = {1, 2, 0, 1, 0};

    for (int i = 0; i < 5; ++i) {
        int newMissionaries = currentState.missionaries;
        int newCannibals = currentState.cannibals;
        int newBoat = currentState.boat == 0 ? 1 : 0;

        if (currentState.boat == 0) {
            newMissionaries -= missionariesMove[i];
            newCannibals -= cannibalsMove[i];
        } else {
            newMissionaries += missionariesMove[i];
            newCannibals += cannibalsMove[i];
        }

        State newState(newMissionaries, newCannibals, newBoat);
        nextStates.push_back(newState);
    }

    return nextStates;
}

vector<State> solve(const State& startState) {
    queue<vector<State>> queue;
    set<State> visited;

    vector<State> startPath = {startState};
    queue.push(startPath);
    visited.insert(startState);

    while (!queue.empty()) {
        vector<State> path = queue.front();
        queue.pop();
        State currentState = path.back();

        if (currentState.isGoal()) {
            return path;
        }

        for (const State& nextState : generateNextStates(currentState)) {
            if (nextState.isValid() && visited.find(nextState) == visited.end()) {
                visited.insert(nextState);
                vector<State> newPath = path;
                newPath.push_back(nextState);
                queue.push(newPath);
            }
        }
    }

    return {};
}

int main() {
    State startState(3, 3, 0);
    vector<State> solution = solve(startState);
    if (!solution.empty()) {
        for (const State& state : solution) {
            cout << state << endl;
        }
    } else {
        cout << "No solution found" << endl;
    }
    return 0;
}
