package io.github.msaggik.cinema.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.msaggik.cinema.databinding.CharacterItemBinding
import io.github.msaggik.cinema.domain.model.character.Character
import io.github.msaggik.ui.R

class CharacterAdapter (characterListAdd: List<Character>, private val characterClickListener: CharacterClickListener) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> () {

    private var characterList = characterListAdd

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        return CharacterViewHolder(CharacterItemBinding.inflate(layoutInspector, parent, false))
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characterList[position])
        holder.itemView.setOnClickListener{
            characterClickListener.onCharacterClick(characterList[position])
        }
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    fun interface CharacterClickListener {
        fun onCharacterClick(character: Character)
    }

    class CharacterViewHolder(private val binding: CharacterItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {
            binding.nameCharacter.text = character.name
            binding.heightCharacter.text = itemView.resources.getString(R.string.height_character_form, character.height)
            binding.dateOfBirth.text = itemView.resources.getString(R.string.date_of_birth_form, character.birthYear)
        }
    }
}