import java.util.ArrayList;
 
 
public class MockMp3Player implements Mp3Player{
 
    private ArrayList songs;
    private int currentSong = 0;
    private boolean isPlaying = false;
    private double songseconds = 0.0;
 
    public MockMp3Player(){
        this.songs = new ArrayList<String>();
    }
 
    @Override
    public void play() {
        if(this.songs.size() > 0){ //if No songs to play
            this.isPlaying = true;
            songseconds += 0.1;
        }
    }
 
    @Override
    public void pause() {
        this.isPlaying = false;
    }
 
    @Override
    public void stop() {
        this.isPlaying = false;
        this.songseconds = 0.0;
    }
 
    @Override
    public double currentPosition() {
        return this.songseconds;
    }
  
    @Override
    public String currentSong() {
        return String.valueOf(this.songs.get(currentSong));
    }
 
    @Override
    public void next() {
        this.songseconds = 0;
        if(this.currentSong < this.songs.size() - 1) // make sure not overflow
            this.currentSong++;  
    }
 
    @Override
    public void prev() {
        this.songseconds = 0;
        if(this.currentSong > 0) 
            this.currentSong--;
    }
 
    @Override
    public boolean isPlaying() {
        return this.isPlaying;
    }
 
    @Override
    public void loadSongs(ArrayList inputList) {
        for (int i=0; i<inputList.size();i++)
            this.songs.add(inputList.get(i));
    }
 
}