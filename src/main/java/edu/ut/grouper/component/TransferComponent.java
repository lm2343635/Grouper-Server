package edu.ut.grouper.component;

import edu.ut.grouper.dao.GroupDao;
import edu.ut.grouper.dao.TransferDao;
import edu.ut.grouper.domain.Group;
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

    @Autowired
    private GroupDao groupDao;

    /**
     * Check and delete transfer objects every minute.
     */
    @Scheduled(fixedRate = 1000 * 60)
    @Transactional
    public void removeTansfers() {
        // Delete transfer by interval of every group.
        for (Group group : groupDao.findAll()) {
            // Skip this group if it has not been initialized.
            if (group.getInterval() == null) {
                continue;
            }
            // Unit of interval time is minute. Use group.getInterval() * 60 here.
            long savetime = System.currentTimeMillis() / 1000L - group.getInterval() * 60;
            List<Transfer> transfers = transferDao.findBeforeSaveTimeInGroup(savetime, group);
            for (Transfer transfer : transfers) {
                transferDao.delete(transfer);
            }
        }
    }

}
