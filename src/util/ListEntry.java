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
		return songName + " " + getArtistName() + " " + albumName + " " + albumYear;
	}
	
	//custom constructor to directly import raw data from CSV
	public ListEntry(String inStr)
	{
		String[] temp = inStr.split("\\|");
		songName = temp[0];
		setArtistName(temp[1]);
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
		if(songName.equals(o.getSongName())) {
			return artistName.compareTo(o.getArtistName());
		}
		else
		{
			return songName.compareTo(o.getSongName());
		}
	}
	
	
}


