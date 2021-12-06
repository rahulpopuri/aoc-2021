package aoc

fun main() {
    val input = "input_day6.txt".toText().split(",").map { it.toInt() }
    val simulation = Simulation(input)
    println("Part 1: " + simulation.simulate(80))
    println("Part 2: " + simulation.simulate(256))
}

private data class Simulation(val data: List<Int>) {
    fun simulate(days: Int): Long {
        // Use a fixed size array to store the number of fish at each age
        // Rotate manually and sum up counts at the end
        var fish = LongArray(9) { 0 }
        data.forEach { d ->
            fish[d]++
        }
        for (day in 0 until days) {
            val temp = fish.clone()
            temp[8] = fish[0]
            temp[7] = fish[8]
            temp[6] = fish[0] + fish[7]
            temp[5] = fish[6]
            temp[4] = fish[5]
            temp[3] = fish[4]
            temp[2] = fish[3]
            temp[1] = fish[2]
            temp[0] = fish[1]
            fish = temp
        }
        return fish.sum()
    }
}
