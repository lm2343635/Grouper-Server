package edu.ut.grouper.component;

import edu.ut.grouper.dao.TransferDao;
import edu.ut.grouper.domain.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
public class TransferComponent {

    @Autowired
    private TransferDao transferDao;

    // Check and delete transfer created 1 hour ago every minute.
    @Scheduled(fixedRate = 1000 * 60)
    @Transactional
    public void removeTansfers() {
        long savetime = System.currentTimeMillis() / 1000 - 3600;
        List<Transfer> transfers = transferDao.findBeforeSaveTime(savetime);
        for (Transfer transfer : transfers) {
            transferDao.delete(transfer);
        }
//        System.out.println("Delete " + transfers.size() + " transfer entities at " + new Date());
    }
}
