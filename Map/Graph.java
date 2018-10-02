package Map;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    public static class Arc { //дуга графа (путь между районами)
	double weight;	// Нагрузка на дугу (пробки)
	int to;			// Номер вершины (района), в которую ведет дуга

	public Arc(int to, double info) {
            this.to = to; this.weight = info;
	}
		
	public double weight() { return weight; }
		
	public int to() { return to; }
	};

    private final List<Arc>[] lGraph;	// Списки смежности
    private final int nVertex;			// Число вершин


    public Graph(int nVert) { //конструктор графа
	lGraph = new List[nVert];
	for (int i = 0; i < nVert; ++i) {
            lGraph[i] = new ArrayList<>();
	}
	nVertex = nVert;
    }
	
    public int getCount() { return nVertex; } //число вершин графа

    /**
    Добавление дуги в граф. Предполагается, что ранее такой дуги в графе не было.
    @param from	Начало дуги (номер вершины)
    @param to	Конец дуги (номер вершины)
    @param info	Нагрузка на дугу
    */
    public void addArc(int from, int to, double info) {
	assert from < nVertex && from >= 0;
	assert to < nVertex && to >= 0;
		
	lGraph[from].add(new Arc(to, info));
        lGraph[to].add(new Arc(from, info)); //удалить если граф ориентированный
	}
	
    public List<Arc> arcs(int u) { //Итератор дуг, ведущих из заданной вершины
	return lGraph[u];
    }
    
}