package bob.gsrv.model;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Game {

	private int id;

	private String name;

	private char[] fields = new char[9];

	private Player playerOne = null;

	private Player playerTwo = null;

	public Game() {
		Arrays.fill(fields, '.');
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTextBoard() {
		final StringBuffer sb = new StringBuffer();
		for (int idx = 0; idx < fields.length; idx++) {
			sb.append(fields[idx]);
			if (0 == (idx % 3)) {
				sb.append('\n');
			}
		}
		return sb.toString();
	}

}
