package top.zetiny.vuemusicproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.zetiny.vuemusicproject.bean.Singer;
import top.zetiny.vuemusicproject.dao.SingerMapper;
import top.zetiny.vuemusicproject.service.SingerService;

import java.util.List;

/**
 * 歌手 Service 层
 */
@Service
public class SingerServiceImpl implements SingerService {

    @Autowired
    private SingerMapper singerMapper;

    @Override
    public List<Singer> querySingerById(Integer id) {
        return singerMapper.selectById(id);
    }

    @Override
    public List<Singer> queryAllSingers() {
        return singerMapper.selectAll();
    }

    @Override
    public List<Singer> querySingersByName(String name) {
        return singerMapper.selectByName(name);
    }

    @Override
    public List<Singer> querySingersBySex(Integer sex) {
        return singerMapper.selectBySex(sex);
    }

    @Override
    public int addSinger(Singer singer) {
        return singerMapper.insert(singer);
    }

    @Override
    public int modifySinger(Singer singer) {
        return singerMapper.update(singer);
    }

    @Override
    public int removeSinger(Integer id) {
        return singerMapper.delete(id);
    }
}
