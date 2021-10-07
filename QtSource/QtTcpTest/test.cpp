#include <tcpsocketform.h>
#include <iostream>
#include <string>

TcpSocketForm *s;
bool run();
bool syntax(QString str);
int main() {

	bool t = run();
	if(t) {
		qDebug() << "Normal end...";
	}
	else {
		qDebug() << "Abnormal end.";
	}
	return 0;
}

bool run() {

	s = new TcpSocketForm();
	qDebug() << s->socket->state() << '\n';
	try {
		while (!(s->socket->state() == 0)) {
			std::cout << "input two values or \"closed\"\n";
			std::string str;
			getline(std::cin, str);

			QString ss = QString::fromStdString(str);
			if(!syntax(ss)) {
				qDebug() << "syntax error";
				continue;
			}
			s->sendLine(ss);
			s->socket->flush();
			QString re = s->readLine().remove('\n').remove('\r');

			if(re == "closed") {
				delete s;
				return true;
			}
			qDebug() << "result value is " << re.toInt();
		}
	} catch (std::runtime_error& e) {
		qDebug() << "exit program..";
		qDebug() << QString(e.what());
		delete s;
		return false;
	}
	delete s;
	return false;
}
bool syntax(QString str) {
	if(!str.compare("closed")) {
		return true;
	}
	bool ok1, ok2;
	QStringList list = str.split(' ');
	if(list.size() != 2) {
		return false;
	}
	list.at(0).toInt(&ok1, 10);
	list.at(1).toInt(&ok2, 10);
	return ok1 && ok2;
}
