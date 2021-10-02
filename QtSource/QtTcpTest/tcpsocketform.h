#ifndef TCPSOCKETFORM_H
#define TCPSOCKETFORM_H

#include <QString>
#include <QTcpSocket>
#include <QDebug>
class TcpSocketForm
{
public:
	TcpSocketForm();
	~TcpSocketForm();
	bool connectToHost(QString host);

	QTcpSocket *socket;
	bool flag = false;
	QString readLine();
	void sendLine(QString str);

};

#endif // TCPSOCKETFORM_H
