package actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.FeedResponse;
import data.Message;
import data.NewsAgentResponse;
import services.FeedService;
import services.NewsAgentService;

import java.util.UUID;

public class MessageActor extends UntypedActor {
    /*Self reference the actor
        define and conigure props
        define objects of feed service and News agent service
        define another actor Reference*/
    private final ActorRef out;
    private FeedService feedService = new FeedService();
    private NewsAgentService newsAgentService = new NewsAgentService();
    private FeedResponse feedResponse=new FeedResponse();


    public MessageActor(ActorRef out)
    {
        this.out = out;
    }

    public static Props props(ActorRef out)
    {
        return Props.create(MessageActor.class, out);
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        //send back the response
        ObjectMapper mapper = new ObjectMapper();
        Message messageObject = new Message();
        if (message instanceof String)
        {

            messageObject.text=(String) message;
            messageObject.sender=Message.Sender.USER;
            out.tell(mapper.writeValueAsString(messageObject), self());
            //writeValueAsString() converts the object into JSON and send it WebSocket
            String query=newsAgentService.getNewsAgentResponse(messageObject.text,UUID.randomUUID()).query;
            FeedResponse feedResponse=feedService.getFeedByQuery(query);
            messageObject.text = (feedResponse.title == null) ? "No results found" : "Showing results for: " + query;
            messageObject.feedResponse=feedResponse;
            messageObject.sender=Message.Sender.BOT;
            out.tell(mapper.writeValueAsString(messageObject), self());

        }
        else
        {

            messageObject.text =  "No results found (not string)";
            messageObject.sender=Message.Sender.BOT;
            out.tell(mapper.writeValueAsString(messageObject), self());


        }


    }
}