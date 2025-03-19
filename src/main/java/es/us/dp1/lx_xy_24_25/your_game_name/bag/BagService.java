package es.us.dp1.lx_xy_24_25.your_game_name.bag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BagService {

    @Autowired
    BagRepository br;

    public BagService(BagRepository br){
        this.br = br;
    }

    @Transactional
    public Bag findByTableroId(Integer tableroId) {
        return br.findBolsaByTableroId(tableroId);
    }

    @Transactional
    public Bag getBolsaById(Integer id) {
        return br.findById(id).get();
    }

    @Transactional
    public void save(Bag bolsa) {
        br.save(bolsa);
    }
    
}
