package com.kuretru.game.sokoban;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelData {
	private static final String SUFFIX = ".sokoban";
	private List<String> raw;
	private List<Point> targetPoints;
	private Cell[][] data;
	private int xLength;
	private int yLength;

	public LevelData(String level) {
		raw = new ArrayList<String>();
		targetPoints = new ArrayList<Point>();
		loadRawFile(String.format("res/level/%s%s", level, SUFFIX));
		xLength = raw.size();
		yLength = getMaxLineLength();
		deserialize();

	}

	// 从资源文件载入关卡数据
	private void loadRawFile(String path) {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.length() != 0) {
					raw.add(line);
				}
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void deserialize() {
		data = new Cell[raw.size()][getMaxLineLength()];
		for (int i = 0; i < xLength; i++) {
			for (int j = 0; j < yLength; j++) {
				data[i][j] = new Cell();
				char c = ' ';
				if (j < raw.get(i).length())
					c = raw.get(i).charAt(j);
				switch (c) {
				case '#':
					data[i][j].type = CellTypeEnum.WALL;
					break;
				case '@':
					data[i][j].type = CellTypeEnum.PERSON;
					break;
				case '$':
					data[i][j].type = CellTypeEnum.BOX;
					break;
				case '.':
					data[i][j].type = CellTypeEnum.TARGET;
					targetPoints.add(new Point(i, j));
					break;
				default:
					data[i][j].type = CellTypeEnum.EMPTY;
					break;
				}
				data[i][j].label = null;
			}
		}
	}

	private int getMaxLineLength() {
		int max = 0;
		for (int i = 0; i < raw.size(); i++) {
			if (raw.get(i).length() > max) {
				max = raw.get(i).length();
			}
		}
		return max;
	}

	// 返回指定行、列的关卡数据
	public Cell get(int x, int y) {
		if (x < 0 || x >= xLength || y < 0 || y >= yLength)
			return null;
		return data[x][y];
	}

	// 返回指定行、列的关卡数据
	public Cell get(Point p) {
		if (p.x < 0 || p.x >= xLength || p.y < 0 || p.y >= yLength)
			return null;
		return data[p.x][p.y];
	}

	// 交换2个点的内容
	public void swap(Point p, Point q) {
		Cell tmp = data[p.x][p.y];
		data[p.x][p.y] = data[q.x][q.y];
		data[q.x][q.y] = tmp;
	}

	// 返回关卡数据的行数
	public int getX() {
		return xLength;
	}

	// 返回关卡数据的列数
	public int getY() {
		return yLength;
	}

	// 判断某个目标是否已经有箱子
	public boolean isArrived(Point p) {
		for (Point point : targetPoints) {
			if (point.x == p.x && point.y == p.y) {
				return true;
			}
		}
		return false;
	}

	// 判断游戏是否通关
	public boolean completed() {
		for (Point point : targetPoints) {
			if (data[point.x][point.y].type != CellTypeEnum.BOX) {
				return false;
			}
		}
		return true;
	}
}
