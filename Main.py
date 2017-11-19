import FIFOQueue
import Event

def main():
	
	queue1 = FIFOQueue.FIFOQueue();
	queue1.push("oi")
	queue1.push("mundo")
	queue1.push("cao")
	print(queue1.in_stack)
	
	print(queue1.pop())
	print(queue1.pop())
	print(queue1.pop())


if __name__ == "__main__":
    main()