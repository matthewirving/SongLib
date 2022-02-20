/*
	Authors: Matt Irving & Will Knox


*/

package util;

import java.util.Comparator;

public class ListEntry implements Comparable<ListEntry> {
	
	private String songName;
	private String artistName;
	private String albumName;
	private String albumYear;
	
	
	public ListEntry(String songName, String artistName)
	{
		this.songName = songName;
		this.artistName = artistName;
		this.albumName = "";
		this.albumYear = "";
	}
	
	public ListEntry(String songName, String artistName, String albumName)
	{
		this.songName = songName;
		this.artistName = artistName;
		this.albumName = albumName;
		this.albumYear = "";
	}
	
	public ListEntry(String songName, String artistName, String albumName, String albumYear)
	{
		this.songName = songName;
		this.artistName = artistName;
		this.albumName = albumName;
		this.albumYear = albumYear;
	}
	
	
	public String toString()
	{
		String temp = songName.trim() + ", " + artistName.trim();
		if(!albumName.trim().isEmpty()) {
			temp += ", " + albumName.trim();
		}
		if(!albumYear.trim().isEmpty()) {
			temp += ", " + albumYear.trim();
		}
		return temp;
	}
	
	//custom constructor to directly import raw data from CSV
	public ListEntry(String inStr)
	{
		String[] temp = inStr.split("\\|");
		songName = temp[0];
		artistName = temp[1];
		albumName = temp[2];
		albumYear = temp[3];
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public String getSongName() {
		return songName;
	}

	public void setSongtName(String songName) {
		this.songName = songName;
	}
	
	public String getAlbumName() {
		return albumName;
	}
	
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	
	public String getAlbumYear()
	{
		return albumYear;
	}
	public void setAlbumYear(String albumYear) {
		this.albumYear = albumYear;
	}
	
	
	@Override
	public int compareTo(ListEntry o) {
		// TODO Auto-generated method stub
		if(songName.trim().equalsIgnoreCase(o.getSongName().trim())) {
			return artistName.trim().compareToIgnoreCase(o.getArtistName().trim());
		}
		else
		{
			return songName.trim().compareToIgnoreCase(o.getSongName().trim());
		}
	}
	
	
}


