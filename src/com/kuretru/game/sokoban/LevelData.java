package com.kuretru.game.sokoban;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelData {
	private static final String SUFFIX = ".sokoban";
	private List<String> data;

	public LevelData(String level) {
		data = new ArrayList<String>();
		loadDataFile(String.format("res/level/%s%s", level, SUFFIX));
	}

	// ����Դ�ļ�����ؿ�����
	private void loadDataFile(String path) {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.length() != 0) {
					data.add(line);
				}
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ��ȡ�����һ�еĳ���
	private int getMaxLineLength() {
		int length = 0;
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).length() > 0) {
				length = data.get(i).length();
			}
		}
		return length;
	}

	// ���عؿ����ݵ�ָ����
	private String get(int index) {
		if (index < 0 || index >= data.size())
			return null;
		return data.get(index);
	}

	// ���عؿ����ݵ�����
	private int size() {
		return data.size();
	}
}
