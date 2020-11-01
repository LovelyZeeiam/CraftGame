package xueLi.gamengine.tool.view;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

public class FileListener extends FileAlterationListenerAdaptor {

    private File listeningFile;
    private ViewMonitor ctx;

    public FileListener(File fileToBeListenedTo, ViewMonitor ctx) {
        this.listeningFile = fileToBeListenedTo;
        this.ctx = ctx;

    }

    @Override
    public void onStart(FileAlterationObserver observer) {
        super.onStart(observer);
    }

    @Override
    public void onFileChange(File file) {
        super.onFileChange(file);

        if (file.getAbsolutePath().equals(this.listeningFile.getAbsolutePath())) {
            ctx.needReload = true;
        }

    }

    @Override
    public void onFileDelete(File file) {
        super.onFileDelete(file);
    }

    @Override
    public void onStop(FileAlterationObserver observer) {
        super.onStop(observer);
    }

}
