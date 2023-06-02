package Services;

import com.flexganttfx.model.Activity;
import com.flexganttfx.model.Row;

public class ModelObject <
    P extends Row<?,?,?>,
        C extends Row<?,?,?>,
    A extends Activity> extends Row<P,C,A>{      }
