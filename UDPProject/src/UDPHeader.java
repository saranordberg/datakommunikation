import java.net.*;

public class UDPHeader extends DatagramSocket {

	public UDPHeader() throws SocketException {
		super();
		// TODO Auto-generated constructor stub
	}

	public UDPHeader(DatagramSocketImpl impl) {
		super(impl);
		// TODO Auto-generated constructor stub
	}

	public UDPHeader(int port, InetAddress laddr) throws SocketException {
		super(port, laddr);
		// TODO Auto-generated constructor stub
	}

	public UDPHeader(int port) throws SocketException {
		super(port);
		// TODO Auto-generated constructor stub
	}

	public UDPHeader(SocketAddress bindaddr) throws SocketException {
		super(bindaddr);
		// TODO Auto-generated constructor stub
	}

}
