package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    SongRepository songRepository;



    @Override
    public void run(String...strings) throws Exception {
        //First let's create a Albun
        Album album = new Album();
        album.setName("Beatles");
        album.setGenre("Ballad");

        //Now let's create a song
        Song song = new Song();
        song.setTitle("Yesterday");
        song.setYear("1962");
        song.setDescription("about memory....");
        song.setAlbum(album);

        //Add the song to an empty list
        Set<Song> songs = new HashSet<Song>();
        songs.add(song);

        song = new Song();
        song.setTitle("Yello submarine");
        song.setYear("2011");
        song.setDescription("lovely song");
        song.setAlbum(album);

        songs.add(song);



        //Add the list of songs to the Album's song list
        album.setSongs(songs);

        //Save the director to the database
        albumRepository.save(album);


    }
}
