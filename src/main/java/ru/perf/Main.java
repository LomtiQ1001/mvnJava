package ru.perf;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import ru.pflb.mq.dummy.exception.DummyException;
import ru.pflb.mq.dummy.implementation.ConnectionImpl;
import ru.pflb.mq.dummy.interfaces.Connection;
import ru.pflb.mq.dummy.interfaces.Destination;
import ru.pflb.mq.dummy.interfaces.Producer;
import ru.pflb.mq.dummy.interfaces.Session;

public class Main {

    public static void main(String[] args) throws DummyException, InterruptedException, IOException {

        Collection<String> list_message = new ArrayList<String>();

        String path = args[3];

        FileReader fr = new FileReader(path);
        Scanner scan = new Scanner(fr);

        while (scan.hasNextLine()) {
            list_message.add(scan.nextLine());
        }

        fr.close();

        Connection connection = new ConnectionImpl();
        connection.start();
        Session session = connection.createSession(true);//new SessionImpl();
        Destination destination = session.createDestination("Queue");
        Producer producer = session.createProducer(destination);

        var iterator = list_message.iterator();
        while (iterator.hasNext()){
            producer.send(iterator.next());
            Thread.sleep(2000);
        }

        session.close();
        connection.close();
    }
}
