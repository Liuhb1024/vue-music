package top.zetiny.vuemusicproject.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.zetiny.vuemusicproject.VueMusicProjectApplication;
import top.zetiny.vuemusicproject.service.SongService;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VueMusicProjectApplication.class)
class SongServiceImplTest {

    @Autowired
    private SongService songService;

    @Test
    void querySongById() {
        System.out.println(songService.querySongById(1));
    }

    @Test
    void queryAllSongs() {
        System.out.println(songService.queryAllSongs());
    }
}