package kaptainwutax.cactusgen.cl;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opencl.*;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public abstract class Program {

    protected IntBuffer ERROR_TRACE = BufferUtils.createIntBuffer(1);

    protected CLPlatform platform = null;
    protected List<CLDevice> devices = new ArrayList<CLDevice>();

    protected CLContext context = null;
    protected CLCommandQueue queue = null;

    public void initialize() throws LWJGLException {
        /*This must be called to load the native libraries.*/
        if(!CL.isCreated())CL.create();

        /*The getPlatforms() method return a List<CLPlatform> which contains the
        available platforms on your hardware.*/
        this.platform = CLPlatform.getPlatforms().get(0);

        /*Gets all the GPUs from the platform and stores them in a list.*/
        this.devices = this.platform.getDevices(CL10.CL_DEVICE_TYPE_GPU);

        /*The context ties the devices, kernels, memory, and queues.*/
        this.context = CLContext.create(this.platform, this.devices, this.ERROR_TRACE);
        Util.checkCLError(this.ERROR_TRACE.get(0));
        this.ERROR_TRACE.clear();

        /*The command queue is created for a specific device and allows commands
        to be sent to the device via a queue. I am assuming the platform only
        has a single GPU.*/
        this.queue = CL10.clCreateCommandQueue(this.context, this.devices.get(0), CL10.CL_QUEUE_PROFILING_ENABLE, this.ERROR_TRACE);
        Util.checkCLError(this.ERROR_TRACE.get(0));
        this.ERROR_TRACE.clear();
    }

}
