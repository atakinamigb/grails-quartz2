package qkiss

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HelloFromExternalConfigJob implements Job {
    private static Logger log = LoggerFactory.getLogger(HelloFromExternalConfigJob.class);
	
	def grailsApplication
	Long orgId
	static boolean ok = false
	static int firedCount = 0

    public HelloFromExternalConfigJob() {}

    void execute(JobExecutionContext jobCtx) {
		assert grailsApplication != null
		assert orgId
		sleep(3000) //sleep 3 seconds so we misfire past all the repeats
		firedCount++

        // Say Hello to the World and display the date/time
        log.info("Hello from - ${jobCtx.jobDetail.key.name} }" )
		//throw JobExecutionException 
		//log.info("MergedMap ${jobCtx.mergedJobDataMap}" )
		def o = Org.get(orgId)
		log.info("got the org $o.name")
		o.name = "in job with time $jobCtx.scheduledFireTime"
		assert o.save()
		ok = true
    }

}
