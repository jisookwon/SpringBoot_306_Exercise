package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
public class HomeController {
    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    SongRepository songRepository;

    @RequestMapping("/")
    public String index(Model model) {
        //First let's create a Albun
        Album album = new Album();
        album.setName("Beatles");
        album.setGenre("Ballard");

        //Now let's create a song
        Song song = new Song();
        song.setTitle("Yesterday");
        song.setYear(1962);
        song.setDescription("about memory....");

        //Add the song to an empty list
        Set<Song> songs = new HashSet<Song>();
        songs.add(song);

        song = new Song();
        song.setTitle("Yello submarine");
        song.setYear(2011);
        song.setDescription("lovely song");
        songs.add(song);

        //Add the list of songs to the Album's song list
        album.setSongs(songs);

        //Save the director to the database
        albumRepository.save(album);

        //Grad all the directors from the database and send them to the plate
        model.addAttribute("albums", albumRepository.findAll());
        return "index";
    }


    //=======Album======================================================================
        @RequestMapping("/")
        public String listAlbums(Model model){
            model.addAttribute("albums", albumRepository.findAll());
            return "list";
        }

        @GetMapping("/addAlbum")
        public String albumForm(Model model){
            model.addAttribute("songs", songRepository.findAll());
            model.addAttribute("album", new Album());
            return "albumForm";
        }

        @PostMapping("/processAlbum")
        public String processForm(@Valid Album album, BindingResult result, Model model){
            model.addAttribute("album", album);

            if (result.hasErrors()){
                model.addAttribute("songs", songRepository.findAll());
                return "albumForm";
            }
            albumRepository.save(album);
            return "redirect:/";
        }

        @RequestMapping("/detail/{id}")
        public String showAlbums(@PathVariable("id") long id, Model model){
            model.addAttribute("album", albumRepository.findById(id).get());
            return "show";
        }

        @RequestMapping("/update/{id}")
        public String updateAlbums(@PathVariable("id") long id, Model model){
            model.addAttribute("songs", songRepository.findAll());
            model.addAttribute("album", albumRepository.findById(id).get());
            return "albumForm";
        }

        @RequestMapping("/delete/{id}")
        public String delAlbums(@PathVariable("id") long id){
            albumRepository.deleteById(id);
            return "redirect:/";
        }
    //====Song===============================================================
    @RequestMapping("/")
    public String (Model model){
        model.addAttribute("songs", songRepository.findAll());
        return "list";
    }

    @GetMapping("/addSongs")
    public String songForm(Model model){
        model.addAttribute("albums", albumRepository.findAll());
        model.addAttribute("song", new Song());
        return "songForm";
    }

    @PostMapping("/processSongs")
    public String processForm(@Valid Album album, BindingResult result, Model model){
        model.addAttribute("song", song);

        if (result.hasErrors()){
            return "songForm";
        }
        albumRepository.save(album);
        return "redirect:/";
    }


}


