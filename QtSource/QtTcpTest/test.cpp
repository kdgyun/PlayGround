#include <tcpsocketform.h>
#include <iostream>
#include <string>

TcpSocketForm *s;
bool run();
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
			std::cout << "input value or closed\n";
			std::string str;
			getline(std::cin, str);

			QString ss = QString::fromStdString(str);

			s->sendLine(ss);
			s->socket->flush();
			QString re = s->readLine();
			re.remove('\n');
			re.remove('\r');
			qDebug() << "read msg : >> " << re;

			if(re == "closed") {
				return true;
				delete s;
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
