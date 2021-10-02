#include "tcpsocketform.h"

TcpSocketForm::TcpSocketForm()
{

	socket = new QTcpSocket();
	flag = connectToHost("127.0.0.1");
	if(!flag) {
		qDebug() << "connected failed\n";
		exit(0);
	}
	qDebug() << "connected...";
}

TcpSocketForm::~TcpSocketForm() {
	delete this->socket;
}

bool TcpSocketForm::connectToHost(QString host) {
	this->socket->connectToHost(host, 5555);
	return this->socket->waitForConnected();
}

QString TcpSocketForm::readLine() {
	int cnt = 0;
	while(!socket->waitForReadyRead(30000) && cnt < 10) {
		cnt++;
		qDebug() << "wait..";
	}
	if(cnt >= 10) {
		 throw std::runtime_error("faild running");
	}
	qDebug() << "wait time : " << cnt ;
	qDebug() << socket->isReadable() ;
	QString s = socket->readLine();
	qDebug() << "read msg : " << s << "\n";
	return s;
}

void TcpSocketForm::sendLine(QString str) {
	while(socket->waitForBytesWritten()) {

	}
	str.append('\n');
	qDebug() << "send msg : " << str;
	socket->write(str.toUtf8());
}
