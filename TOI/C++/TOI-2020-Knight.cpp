#include <iostream>
#include <queue>

using namespace std;

int dx[] = {1,1,-1,-1,2,2,-2,-2};
int dy[] = {2,-2,2,-2,1,-1,1,-1};
struct undo {
    int ind1;
    int ind2;
    int costo;
    undo(int a, int b, int c){
        ind1 = a;
        ind2 = b;
        costo = c;
    }
};

int dfs(int a, int b, int m, int n, int Mx, int My){
    queue<undo> Q;
    int ggg=0,sss=Mx*My;
    Q.push(undo(a, b, 0));
    while(!Q.empty()){
        undo aux = Q.front();
        Q.pop();
        if (aux.ind1 == m && aux.ind2 == n) return aux.costo;
        else if(ggg== sss){aux.costo=-1;return aux.costo;}

        for (int i = 0; i < 8; i++){
            int I = aux.ind1 + dx[i];
            int J = aux.ind2 + dy[i];
            if (I >= 1 && J >= 1 && I <= Mx && J <= My) {
                Q.push(undo(I, J, aux.costo + 1));
                ggg++;
            }
        }
    }
}


int main() {
    int x, y, Xz, Yz, Mx, My;
    cin >> Mx >> My >> x >> y >> Xz >> Yz;
    int result = dfs(x+1, y+1, Xz+1, Yz+1, Mx, My);
    cout << result<<endl;
    return 0;
}
