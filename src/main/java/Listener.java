import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import org.testng.IExecutionListener;

/**
 * Created by GXP8655 on 3/22/2017.
 */
public class Listener implements IExecutionListener {
    public void onExecutionFinish() {
        if (Reports.report != null) {
            Reports.report.flush();
            Reports.report.close();
        }
    }

    public void onExecutionStart() {
        Reports.report = new ExtentReports("./reports/ExecutionReport.html", true, DisplayOrder.OLDEST_FIRST);
    }
}
