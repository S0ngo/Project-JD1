package Dijkstra;

import Map.Graph;

import java.util.LinkedList;
import java.util.List;

/**
 * Реализация алгоритма Дейкстры поиска минимальных путей в графе
 * с неотрицательной нагрузкой на дугах.
 * Хранение текущих расстояний до вершин графа производится в
 * двоичной куче.
 */
public class Dijkstra {
    final Graph graph;		// Граф, для которого производятся вычисления
	
    int src = -1;			// Начальная вершина, пути из которой анализируются
    int nVert;				// число вершин в графе
	
    double[] distances;		// Массив расстояний
    int[] tree;				// Дерево обхода по минимальным путям
	
    int[] positions;		// Индексы вершин в куче		
    int[] binHeap;			// Двоичная куча
    int heapSize = 0;		// Размер кучи
	
    public Dijkstra(Graph g) {
	graph = g;
        nVert = g.getCount();
        distances = new double[nVert];
	tree = new int[nVert];
	positions = new int[nVert];
	binHeap = new int[nVert];
    }
	
    /**
    * Выдает дерево минимальных путей.
    * Если дерево еще не построено, запускается алгоритм Дейкстры.
    * @param from	Номер исходной вершины
    * @return		Дерево в виде массива обратных дуг
    */
    public int[] getTree(int from) {
	if (from < 0 || from >= nVert) return null;
	// Был ли запущен алгоритм Дейкстры из заданной начальной вершины?
            checkStart(from);
	return tree;
    }
	
    /**
    * Выдает длины минимальных путей.
    * Если дерево еще не построено, запускается алгоритм Дейкстры.
    * @param from	Номер исходной вершины
    * @return		Массив расстояний до указанных вершин
    */
    public double[] getDistances(int from) {
        if (from < 0 || from >= nVert) return null; // Был ли запущен алгоритм Дейкстры из заданной начальной вершины?
            checkStart(from);
	return distances;
    }
	
    /**
    * Выдает кратчайший путь между двумя вершинами.
    * @param from	Номер начальной вершины.
    * @param to	Номер конечной вершины.
    * @return		Список вершин на пути от начальной вершины к конечной.
    */
    public List<Integer> getPath(int from, int to) {
	if (from < 0 || from >= nVert || to < 0 || to >= nVert) return null; // Был ли запущен алгоритм Дейкстры из заданной начальной вершины?
        checkStart(from);
	if (distances[to] == Integer.MAX_VALUE) {
            return null;
	} else {
            LinkedList<Integer> path = new LinkedList<>();
            path.add(to);
            while (tree[to] != -1) {
		path.addFirst(to = tree[to]);
            }
	return path;
	}
    }
	
    /**
    * Реализация алгоритма Дейкстры.
    * В результате работы алгоритма будет построено
    * дерево минимальных путей и определены их длины.
    * @param s	Начальная вершина
    */
    public void dijkstra(int s) {
        src = s;
        
	for (int i = 0; i < nVert; ++i) { // Инициализация массивов
            distances[i] = Double.POSITIVE_INFINITY;
            tree[i] = -1;
            positions[i] = -1;
	}
	distances[s] = 0;
		
	// Инициализация кучи
	heapSize = 1;
	binHeap[0] = s;
		
	while (heapSize > 0) {
            // Жадный алгоритм выбирает ближайшую вершину
            int vert = extractHeap();
			
            // Производим релаксацию дуг, ведущих из выбранной вершины
            for (Graph.Arc arc : graph.arcs(vert)) {
		int end = arc.to();
                double newDist = distances[vert] + arc.weight();
                if (distances[end] <= newDist) continue;
		tree[end] = vert;
		distances[end] = newDist;
		if (positions[end] == -1) {
                    // Новая вершина - добавляем в кучу
                    binHeap[heapSize] = end;
                    positions[end] = heapSize++;
		}
		updatePrio(positions[end]);
            }
	}
    }
    
    //-----------------Служебные методы класса (Private)---------------------
	
    /**
    * Проверка, был ли запущен алгоритм Дейкстры из заданной начальной вершины.
    * Если нет - алгоритм запускается.
    * @param from	Исходная вершина.
    */
    private void checkStart(int from) {
	if (from != src) {
            dijkstra(from);
	}
    }

    /**
    * Изменение позиции элемента в куче в соответствии с изменившимся
    * (уменьшившимся) расстоянием до нее.
    * @param i			Позиция элемента в куче
    */
    private void updatePrio(int i) {
        int vert = binHeap[i];
	int pred = (i - 1) / 2;
	while (pred >= 0 && distances[vert] < distances[binHeap[pred]]) {
            positions[binHeap[pred]] = i;
            binHeap[i] = binHeap[pred];
            i = pred;
            if (pred == 0) break;
		pred = (i - 1) / 2;
	}
	binHeap[i] = vert;
	positions[vert] = i;
    }

    /**
    * Извлечение из кучи элемента с минимальным расстоянием до него.
    * @return	Элемент с наивысшим приоритетом (наименьшим расстоянием).
    */
    private int extractHeap() {
        int minVert = binHeap[0];
	positions[minVert] = -1;
	if (--heapSize > 0) {
            binHeap[0] = binHeap[heapSize];
            positions[binHeap[0]] = 0;
            heapDown(0);
	}
	return minVert;
    }

    /**
    * Протаскивание элемента кучи с заданным индексом вниз по куче
    * @param i	Индекс элемента
    */
    private void heapDown(int i) {
        int pair = binHeap[i];
	int next = 2 * i + 1;
	while (next < heapSize) {
            if (next + 1 < heapSize && distances[binHeap[next+1]] < distances[binHeap[next]]) {
		next++;
            }
            if (distances[pair] <= distances[binHeap[next]]) {
		break;
            }
            positions[binHeap[next]] = i;
            binHeap[i] = binHeap[next];
            i = next;
            next = 2 * i + 1;
	}
	positions[pair] = i;
	binHeap[i] = pair;
    }
    
}