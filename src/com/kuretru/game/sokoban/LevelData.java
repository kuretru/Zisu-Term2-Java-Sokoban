package com.kuretru.game.sokoban;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelData {
	private static final String SUFFIX = ".sokoban";
	private List<String> raw;
	private Cell[][] data;
	private int xLength;
	private int yLength;

	public LevelData(String level) {
		raw = new ArrayList<String>();
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

	// 返回关卡数据的行数
	public int getX() {
		return xLength;
	}

	// 返回关卡数据的列数
	public int getY() {
		return yLength;
	}
}
